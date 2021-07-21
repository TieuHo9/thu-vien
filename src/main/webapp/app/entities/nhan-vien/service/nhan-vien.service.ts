import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INhanVien, getNhanVienIdentifier } from '../nhan-vien.model';

export type EntityResponseType = HttpResponse<INhanVien>;
export type EntityArrayResponseType = HttpResponse<INhanVien[]>;

@Injectable({ providedIn: 'root' })
export class NhanVienService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/nhan-viens');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(nhanVien: INhanVien): Observable<EntityResponseType> {
    return this.http.post<INhanVien>(this.resourceUrl, nhanVien, { observe: 'response' });
  }

  update(nhanVien: INhanVien): Observable<EntityResponseType> {
    return this.http.put<INhanVien>(`${this.resourceUrl}/${getNhanVienIdentifier(nhanVien) as number}`, nhanVien, { observe: 'response' });
  }

  partialUpdate(nhanVien: INhanVien): Observable<EntityResponseType> {
    return this.http.patch<INhanVien>(`${this.resourceUrl}/${getNhanVienIdentifier(nhanVien) as number}`, nhanVien, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INhanVien>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INhanVien[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addNhanVienToCollectionIfMissing(nhanVienCollection: INhanVien[], ...nhanViensToCheck: (INhanVien | null | undefined)[]): INhanVien[] {
    const nhanViens: INhanVien[] = nhanViensToCheck.filter(isPresent);
    if (nhanViens.length > 0) {
      const nhanVienCollectionIdentifiers = nhanVienCollection.map(nhanVienItem => getNhanVienIdentifier(nhanVienItem)!);
      const nhanViensToAdd = nhanViens.filter(nhanVienItem => {
        const nhanVienIdentifier = getNhanVienIdentifier(nhanVienItem);
        if (nhanVienIdentifier == null || nhanVienCollectionIdentifiers.includes(nhanVienIdentifier)) {
          return false;
        }
        nhanVienCollectionIdentifiers.push(nhanVienIdentifier);
        return true;
      });
      return [...nhanViensToAdd, ...nhanVienCollection];
    }
    return nhanVienCollection;
  }
}
