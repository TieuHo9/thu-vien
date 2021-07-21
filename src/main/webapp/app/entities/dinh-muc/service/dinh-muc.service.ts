import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDinhMuc, getDinhMucIdentifier } from '../dinh-muc.model';

export type EntityResponseType = HttpResponse<IDinhMuc>;
export type EntityArrayResponseType = HttpResponse<IDinhMuc[]>;

@Injectable({ providedIn: 'root' })
export class DinhMucService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/dinh-mucs');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(dinhMuc: IDinhMuc): Observable<EntityResponseType> {
    return this.http.post<IDinhMuc>(this.resourceUrl, dinhMuc, { observe: 'response' });
  }

  update(dinhMuc: IDinhMuc): Observable<EntityResponseType> {
    return this.http.put<IDinhMuc>(`${this.resourceUrl}/${getDinhMucIdentifier(dinhMuc) as number}`, dinhMuc, { observe: 'response' });
  }

  partialUpdate(dinhMuc: IDinhMuc): Observable<EntityResponseType> {
    return this.http.patch<IDinhMuc>(`${this.resourceUrl}/${getDinhMucIdentifier(dinhMuc) as number}`, dinhMuc, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDinhMuc>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDinhMuc[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDinhMucToCollectionIfMissing(dinhMucCollection: IDinhMuc[], ...dinhMucsToCheck: (IDinhMuc | null | undefined)[]): IDinhMuc[] {
    const dinhMucs: IDinhMuc[] = dinhMucsToCheck.filter(isPresent);
    if (dinhMucs.length > 0) {
      const dinhMucCollectionIdentifiers = dinhMucCollection.map(dinhMucItem => getDinhMucIdentifier(dinhMucItem)!);
      const dinhMucsToAdd = dinhMucs.filter(dinhMucItem => {
        const dinhMucIdentifier = getDinhMucIdentifier(dinhMucItem);
        if (dinhMucIdentifier == null || dinhMucCollectionIdentifiers.includes(dinhMucIdentifier)) {
          return false;
        }
        dinhMucCollectionIdentifiers.push(dinhMucIdentifier);
        return true;
      });
      return [...dinhMucsToAdd, ...dinhMucCollection];
    }
    return dinhMucCollection;
  }
}
