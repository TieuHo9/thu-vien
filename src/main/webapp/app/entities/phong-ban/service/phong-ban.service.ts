import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPhongBan, getPhongBanIdentifier } from '../phong-ban.model';

export type EntityResponseType = HttpResponse<IPhongBan>;
export type EntityArrayResponseType = HttpResponse<IPhongBan[]>;

@Injectable({ providedIn: 'root' })
export class PhongBanService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/phong-bans');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(phongBan: IPhongBan): Observable<EntityResponseType> {
    return this.http.post<IPhongBan>(this.resourceUrl, phongBan, { observe: 'response' });
  }

  update(phongBan: IPhongBan): Observable<EntityResponseType> {
    return this.http.put<IPhongBan>(`${this.resourceUrl}/${getPhongBanIdentifier(phongBan) as number}`, phongBan, { observe: 'response' });
  }

  partialUpdate(phongBan: IPhongBan): Observable<EntityResponseType> {
    return this.http.patch<IPhongBan>(`${this.resourceUrl}/${getPhongBanIdentifier(phongBan) as number}`, phongBan, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPhongBan>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPhongBan[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPhongBanToCollectionIfMissing(phongBanCollection: IPhongBan[], ...phongBansToCheck: (IPhongBan | null | undefined)[]): IPhongBan[] {
    const phongBans: IPhongBan[] = phongBansToCheck.filter(isPresent);
    if (phongBans.length > 0) {
      const phongBanCollectionIdentifiers = phongBanCollection.map(phongBanItem => getPhongBanIdentifier(phongBanItem)!);
      const phongBansToAdd = phongBans.filter(phongBanItem => {
        const phongBanIdentifier = getPhongBanIdentifier(phongBanItem);
        if (phongBanIdentifier == null || phongBanCollectionIdentifiers.includes(phongBanIdentifier)) {
          return false;
        }
        phongBanCollectionIdentifiers.push(phongBanIdentifier);
        return true;
      });
      return [...phongBansToAdd, ...phongBanCollection];
    }
    return phongBanCollection;
  }
}
