import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INhanVienCongTac, getNhanVienCongTacIdentifier } from '../nhan-vien-cong-tac.model';

export type EntityResponseType = HttpResponse<INhanVienCongTac>;
export type EntityArrayResponseType = HttpResponse<INhanVienCongTac[]>;

@Injectable({ providedIn: 'root' })
export class NhanVienCongTacService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/nhan-vien-cong-tacs');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(nhanVienCongTac: INhanVienCongTac): Observable<EntityResponseType> {
    return this.http.post<INhanVienCongTac>(this.resourceUrl, nhanVienCongTac, { observe: 'response' });
  }

  update(nhanVienCongTac: INhanVienCongTac): Observable<EntityResponseType> {
    return this.http.put<INhanVienCongTac>(
      `${this.resourceUrl}/${getNhanVienCongTacIdentifier(nhanVienCongTac) as number}`,
      nhanVienCongTac,
      { observe: 'response' }
    );
  }

  partialUpdate(nhanVienCongTac: INhanVienCongTac): Observable<EntityResponseType> {
    return this.http.patch<INhanVienCongTac>(
      `${this.resourceUrl}/${getNhanVienCongTacIdentifier(nhanVienCongTac) as number}`,
      nhanVienCongTac,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INhanVienCongTac>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INhanVienCongTac[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addNhanVienCongTacToCollectionIfMissing(
    nhanVienCongTacCollection: INhanVienCongTac[],
    ...nhanVienCongTacsToCheck: (INhanVienCongTac | null | undefined)[]
  ): INhanVienCongTac[] {
    const nhanVienCongTacs: INhanVienCongTac[] = nhanVienCongTacsToCheck.filter(isPresent);
    if (nhanVienCongTacs.length > 0) {
      const nhanVienCongTacCollectionIdentifiers = nhanVienCongTacCollection.map(
        nhanVienCongTacItem => getNhanVienCongTacIdentifier(nhanVienCongTacItem)!
      );
      const nhanVienCongTacsToAdd = nhanVienCongTacs.filter(nhanVienCongTacItem => {
        const nhanVienCongTacIdentifier = getNhanVienCongTacIdentifier(nhanVienCongTacItem);
        if (nhanVienCongTacIdentifier == null || nhanVienCongTacCollectionIdentifiers.includes(nhanVienCongTacIdentifier)) {
          return false;
        }
        nhanVienCongTacCollectionIdentifiers.push(nhanVienCongTacIdentifier);
        return true;
      });
      return [...nhanVienCongTacsToAdd, ...nhanVienCongTacCollection];
    }
    return nhanVienCongTacCollection;
  }
}
