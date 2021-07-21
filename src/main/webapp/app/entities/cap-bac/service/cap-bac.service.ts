import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICapBac, getCapBacIdentifier } from '../cap-bac.model';

export type EntityResponseType = HttpResponse<ICapBac>;
export type EntityArrayResponseType = HttpResponse<ICapBac[]>;

@Injectable({ providedIn: 'root' })
export class CapBacService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/cap-bacs');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(capBac: ICapBac): Observable<EntityResponseType> {
    return this.http.post<ICapBac>(this.resourceUrl, capBac, { observe: 'response' });
  }

  update(capBac: ICapBac): Observable<EntityResponseType> {
    return this.http.put<ICapBac>(`${this.resourceUrl}/${getCapBacIdentifier(capBac) as number}`, capBac, { observe: 'response' });
  }

  partialUpdate(capBac: ICapBac): Observable<EntityResponseType> {
    return this.http.patch<ICapBac>(`${this.resourceUrl}/${getCapBacIdentifier(capBac) as number}`, capBac, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICapBac>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICapBac[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCapBacToCollectionIfMissing(capBacCollection: ICapBac[], ...capBacsToCheck: (ICapBac | null | undefined)[]): ICapBac[] {
    const capBacs: ICapBac[] = capBacsToCheck.filter(isPresent);
    if (capBacs.length > 0) {
      const capBacCollectionIdentifiers = capBacCollection.map(capBacItem => getCapBacIdentifier(capBacItem)!);
      const capBacsToAdd = capBacs.filter(capBacItem => {
        const capBacIdentifier = getCapBacIdentifier(capBacItem);
        if (capBacIdentifier == null || capBacCollectionIdentifiers.includes(capBacIdentifier)) {
          return false;
        }
        capBacCollectionIdentifiers.push(capBacIdentifier);
        return true;
      });
      return [...capBacsToAdd, ...capBacCollection];
    }
    return capBacCollection;
  }
}
