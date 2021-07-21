import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IChiPhi, getChiPhiIdentifier } from '../chi-phi.model';

export type EntityResponseType = HttpResponse<IChiPhi>;
export type EntityArrayResponseType = HttpResponse<IChiPhi[]>;

@Injectable({ providedIn: 'root' })
export class ChiPhiService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/chi-phis');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(chiPhi: IChiPhi): Observable<EntityResponseType> {
    return this.http.post<IChiPhi>(this.resourceUrl, chiPhi, { observe: 'response' });
  }

  update(chiPhi: IChiPhi): Observable<EntityResponseType> {
    return this.http.put<IChiPhi>(`${this.resourceUrl}/${getChiPhiIdentifier(chiPhi) as number}`, chiPhi, { observe: 'response' });
  }

  partialUpdate(chiPhi: IChiPhi): Observable<EntityResponseType> {
    return this.http.patch<IChiPhi>(`${this.resourceUrl}/${getChiPhiIdentifier(chiPhi) as number}`, chiPhi, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IChiPhi>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IChiPhi[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addChiPhiToCollectionIfMissing(chiPhiCollection: IChiPhi[], ...chiPhisToCheck: (IChiPhi | null | undefined)[]): IChiPhi[] {
    const chiPhis: IChiPhi[] = chiPhisToCheck.filter(isPresent);
    if (chiPhis.length > 0) {
      const chiPhiCollectionIdentifiers = chiPhiCollection.map(chiPhiItem => getChiPhiIdentifier(chiPhiItem)!);
      const chiPhisToAdd = chiPhis.filter(chiPhiItem => {
        const chiPhiIdentifier = getChiPhiIdentifier(chiPhiItem);
        if (chiPhiIdentifier == null || chiPhiCollectionIdentifiers.includes(chiPhiIdentifier)) {
          return false;
        }
        chiPhiCollectionIdentifiers.push(chiPhiIdentifier);
        return true;
      });
      return [...chiPhisToAdd, ...chiPhiCollection];
    }
    return chiPhiCollection;
  }
}
