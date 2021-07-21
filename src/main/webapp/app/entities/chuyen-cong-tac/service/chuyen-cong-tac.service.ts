import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IChuyenCongTac, getChuyenCongTacIdentifier } from '../chuyen-cong-tac.model';

export type EntityResponseType = HttpResponse<IChuyenCongTac>;
export type EntityArrayResponseType = HttpResponse<IChuyenCongTac[]>;

@Injectable({ providedIn: 'root' })
export class ChuyenCongTacService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/chuyen-cong-tacs');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(chuyenCongTac: IChuyenCongTac): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(chuyenCongTac);
    return this.http
      .post<IChuyenCongTac>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(chuyenCongTac: IChuyenCongTac): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(chuyenCongTac);
    return this.http
      .put<IChuyenCongTac>(`${this.resourceUrl}/${getChuyenCongTacIdentifier(chuyenCongTac) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(chuyenCongTac: IChuyenCongTac): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(chuyenCongTac);
    return this.http
      .patch<IChuyenCongTac>(`${this.resourceUrl}/${getChuyenCongTacIdentifier(chuyenCongTac) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IChuyenCongTac>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IChuyenCongTac[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addChuyenCongTacToCollectionIfMissing(
    chuyenCongTacCollection: IChuyenCongTac[],
    ...chuyenCongTacsToCheck: (IChuyenCongTac | null | undefined)[]
  ): IChuyenCongTac[] {
    const chuyenCongTacs: IChuyenCongTac[] = chuyenCongTacsToCheck.filter(isPresent);
    if (chuyenCongTacs.length > 0) {
      const chuyenCongTacCollectionIdentifiers = chuyenCongTacCollection.map(
        chuyenCongTacItem => getChuyenCongTacIdentifier(chuyenCongTacItem)!
      );
      const chuyenCongTacsToAdd = chuyenCongTacs.filter(chuyenCongTacItem => {
        const chuyenCongTacIdentifier = getChuyenCongTacIdentifier(chuyenCongTacItem);
        if (chuyenCongTacIdentifier == null || chuyenCongTacCollectionIdentifiers.includes(chuyenCongTacIdentifier)) {
          return false;
        }
        chuyenCongTacCollectionIdentifiers.push(chuyenCongTacIdentifier);
        return true;
      });
      return [...chuyenCongTacsToAdd, ...chuyenCongTacCollection];
    }
    return chuyenCongTacCollection;
  }

  protected convertDateFromClient(chuyenCongTac: IChuyenCongTac): IChuyenCongTac {
    return Object.assign({}, chuyenCongTac, {
      thoiGianTu: chuyenCongTac.thoiGianTu?.isValid() ? chuyenCongTac.thoiGianTu.format(DATE_FORMAT) : undefined,
      thoiGianDen: chuyenCongTac.thoiGianDen?.isValid() ? chuyenCongTac.thoiGianDen.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.thoiGianTu = res.body.thoiGianTu ? dayjs(res.body.thoiGianTu) : undefined;
      res.body.thoiGianDen = res.body.thoiGianDen ? dayjs(res.body.thoiGianDen) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((chuyenCongTac: IChuyenCongTac) => {
        chuyenCongTac.thoiGianTu = chuyenCongTac.thoiGianTu ? dayjs(chuyenCongTac.thoiGianTu) : undefined;
        chuyenCongTac.thoiGianDen = chuyenCongTac.thoiGianDen ? dayjs(chuyenCongTac.thoiGianDen) : undefined;
      });
    }
    return res;
  }
}
