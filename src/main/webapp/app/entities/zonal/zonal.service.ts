import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IZonal } from 'app/shared/model/zonal.model';

type EntityResponseType = HttpResponse<IZonal>;
type EntityArrayResponseType = HttpResponse<IZonal[]>;

@Injectable({ providedIn: 'root' })
export class ZonalService {
    private resourceUrl = SERVER_API_URL + 'api/zonals';

    constructor(private http: HttpClient) {}

    create(zonal: IZonal): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(zonal);
        return this.http
            .post<IZonal>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(zonal: IZonal): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(zonal);
        return this.http
            .put<IZonal>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IZonal>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IZonal[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(zonal: IZonal): IZonal {
        const copy: IZonal = Object.assign({}, zonal, {
            date: zonal.date != null && zonal.date.isValid() ? zonal.date.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.date = res.body.date != null ? moment(res.body.date) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((zonal: IZonal) => {
            zonal.date = zonal.date != null ? moment(zonal.date) : null;
        });
        return res;
    }
}
