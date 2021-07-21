import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPhongBanNhanVien, getPhongBanNhanVienIdentifier } from '../phong-ban-nhan-vien.model';

export type EntityResponseType = HttpResponse<IPhongBanNhanVien>;
export type EntityArrayResponseType = HttpResponse<IPhongBanNhanVien[]>;

@Injectable({ providedIn: 'root' })
export class PhongBanNhanVienService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/phong-ban-nhan-viens');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(phongBanNhanVien: IPhongBanNhanVien): Observable<EntityResponseType> {
    return this.http.post<IPhongBanNhanVien>(this.resourceUrl, phongBanNhanVien, { observe: 'response' });
  }

  update(phongBanNhanVien: IPhongBanNhanVien): Observable<EntityResponseType> {
    return this.http.put<IPhongBanNhanVien>(
      `${this.resourceUrl}/${getPhongBanNhanVienIdentifier(phongBanNhanVien) as number}`,
      phongBanNhanVien,
      { observe: 'response' }
    );
  }

  partialUpdate(phongBanNhanVien: IPhongBanNhanVien): Observable<EntityResponseType> {
    return this.http.patch<IPhongBanNhanVien>(
      `${this.resourceUrl}/${getPhongBanNhanVienIdentifier(phongBanNhanVien) as number}`,
      phongBanNhanVien,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPhongBanNhanVien>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPhongBanNhanVien[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPhongBanNhanVienToCollectionIfMissing(
    phongBanNhanVienCollection: IPhongBanNhanVien[],
    ...phongBanNhanViensToCheck: (IPhongBanNhanVien | null | undefined)[]
  ): IPhongBanNhanVien[] {
    const phongBanNhanViens: IPhongBanNhanVien[] = phongBanNhanViensToCheck.filter(isPresent);
    if (phongBanNhanViens.length > 0) {
      const phongBanNhanVienCollectionIdentifiers = phongBanNhanVienCollection.map(
        phongBanNhanVienItem => getPhongBanNhanVienIdentifier(phongBanNhanVienItem)!
      );
      const phongBanNhanViensToAdd = phongBanNhanViens.filter(phongBanNhanVienItem => {
        const phongBanNhanVienIdentifier = getPhongBanNhanVienIdentifier(phongBanNhanVienItem);
        if (phongBanNhanVienIdentifier == null || phongBanNhanVienCollectionIdentifiers.includes(phongBanNhanVienIdentifier)) {
          return false;
        }
        phongBanNhanVienCollectionIdentifiers.push(phongBanNhanVienIdentifier);
        return true;
      });
      return [...phongBanNhanViensToAdd, ...phongBanNhanVienCollection];
    }
    return phongBanNhanVienCollection;
  }
}
