import { Injectable } from '@angular/core';
import { Headers, Http, Response } from '@angular/http';

import 'rxjs/add/operator/toPromise';

@Injectable()
export class NewsService {

    private apiUrl = 'http://localhost:3001/api/news/';
    private apiGet: string = this.apiUrl + 'get';
    constructor(private http: Http) { }

    getNews() {
        return this.http.get(this.apiGet).toPromise().then(res => res.json()).catch(this.handleError);
    }

    handleError(error: any): Promise<any> {
        console.error('An error occurred: ', error);
        return Promise.reject(error.message || error);
    }

}
