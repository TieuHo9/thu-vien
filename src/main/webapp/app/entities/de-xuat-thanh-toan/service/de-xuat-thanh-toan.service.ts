import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDeXuatThanhToan, getDeXuatThanhToanIdentifier } from '../de-xuat-thanh-toan.model';

export type EntityResponseType = HttpResponse<IDeXuatThanhToan>;
export type EntityArrayResponseType = HttpResponse<IDeXuatThanhToan[]>;

@Injectable({ providedIn: 'root' })
export class DeXuatThanhToanService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/de-xuat-thanh-toans');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  public getName(tenDeXuat: string): Observable<IDeXuatThanhToan> {
    const urlname = `${this.resourceUrl}/name/${tenDeXuat}`;
    return this.http.get<IDeXuatThanhToan>(urlname);
  }

  public getbaocaodathanhtoan(): Observable<IDeXuatThanhToan> {
    const urlname = `${this.resourceUrl}/thanhtoan/`;
    return this.http.get<IDeXuatThanhToan>(urlname);
  }

  create(deXuatThanhToan: IDeXuatThanhToan): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deXuatThanhToan);
    return this.http
      .post<IDeXuatThanhToan>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(deXuatThanhToan: IDeXuatThanhToan): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deXuatThanhToan);
    return this.http
      .put<IDeXuatThanhToan>(`${this.resourceUrl}/${getDeXuatThanhToanIdentifier(deXuatThanhToan) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(deXuatThanhToan: IDeXuatThanhToan): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deXuatThanhToan);
    return this.http
      .patch<IDeXuatThanhToan>(`${this.resourceUrl}/${getDeXuatThanhToanIdentifier(deXuatThanhToan) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDeXuatThanhToan>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDeXuatThanhToan[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDeXuatThanhToanToCollectionIfMissing(
    deXuatThanhToanCollection: IDeXuatThanhToan[],
    ...deXuatThanhToansToCheck: (IDeXuatThanhToan | null | undefined)[]
  ): IDeXuatThanhToan[] {
    const deXuatThanhToans: IDeXuatThanhToan[] = deXuatThanhToansToCheck.filter(isPresent);
    if (deXuatThanhToans.length > 0) {
      const deXuatThanhToanCollectionIdentifiers = deXuatThanhToanCollection.map(
        deXuatThanhToanItem => getDeXuatThanhToanIdentifier(deXuatThanhToanItem)!
      );
      const deXuatThanhToansToAdd = deXuatThanhToans.filter(deXuatThanhToanItem => {
        const deXuatThanhToanIdentifier = getDeXuatThanhToanIdentifier(deXuatThanhToanItem);
        if (deXuatThanhToanIdentifier == null || deXuatThanhToanCollectionIdentifiers.includes(deXuatThanhToanIdentifier)) {
          return false;
        }
        deXuatThanhToanCollectionIdentifiers.push(deXuatThanhToanIdentifier);
        return true;
      });
      return [...deXuatThanhToansToAdd, ...deXuatThanhToanCollection];
    }
    return deXuatThanhToanCollection;
  }

  protected convertDateFromClient(deXuatThanhToan: IDeXuatThanhToan): IDeXuatThanhToan {
    return Object.assign({}, deXuatThanhToan, {
      tuNgay: deXuatThanhToan.tuNgay?.isValid() ? deXuatThanhToan.tuNgay.format(DATE_FORMAT) : undefined,
      denNgay: deXuatThanhToan.denNgay?.isValid() ? deXuatThanhToan.denNgay.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.tuNgay = res.body.tuNgay ? dayjs(res.body.tuNgay) : undefined;
      res.body.denNgay = res.body.denNgay ? dayjs(res.body.denNgay) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((deXuatThanhToan: IDeXuatThanhToan) => {
        deXuatThanhToan.tuNgay = deXuatThanhToan.tuNgay ? dayjs(deXuatThanhToan.tuNgay) : undefined;
        deXuatThanhToan.denNgay = deXuatThanhToan.denNgay ? dayjs(deXuatThanhToan.denNgay) : undefined;
      });
    }
    return res;
  }
}
