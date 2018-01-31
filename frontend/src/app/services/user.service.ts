import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
import {Account} from '../models/account';
import {Profile} from '../models/profile';
import {Observable} from 'rxjs/Observable';
import {Constants} from './const';

@Injectable()
export class UserService {
    constructor(private http: Http) { }

    getUserFromStorage() {
        return JSON.parse(localStorage.getItem('currentUser'));
    }

    create(user: Account) {
        return this.http.post(Constants.HOME_URL + 'users', user, this.jwt()).map((response: Response) => {
            localStorage.setItem('currentUser', JSON.stringify(user));
            response.json();
        });
    }

    getFullUserInfo(id): Observable<Profile> {
        return this.http.get(Constants.HOME_URL + 'profiles?user_id=' + id, this.jwt())
            .map((response: Response) => response.json());

    }

    // private helper methods

    private jwt() {
        // create authorization header with jwt token
        const currentUser = JSON.parse(localStorage.getItem('currentUser'));
        if (currentUser && currentUser.token) {
            const headers = new Headers({ 'Authorization': 'Bearer ' + currentUser.token });
            return new RequestOptions({ headers: headers });
        }
    }
}
