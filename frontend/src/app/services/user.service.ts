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
        return localStorage.getItem('token');
    }

    create(user: Account) {
        return this.http.post(Constants.HOME_URL + 'users', user, this.jwt()).map((response: Response) => {
            localStorage.setItem('token', JSON.stringify(user));
            response.json();
        });
    }

    getFullUserInfo(): Observable<Profile> {
        return this.http.get(Constants.HOME_URL + 'accounts/account/user', this.jwt())
            .map((response: Response) => response.json());

    }

    saveFullUserInfo(profile: Profile): Observable<Profile> {
        return this.http.post(Constants.HOME_URL + 'accounts/account/user', this.getUserFromStorage(), this.jwt())
            .map((response: Response) => response.json());

    }

    // private helper methods

    private jwt() {
        // create authorization header with jwt token
        const token = this.getUserFromStorage();
        if (token) {
            const headers = new Headers({ 'Authorization': token });
            return new RequestOptions({ headers: headers });
        }
    }
}
