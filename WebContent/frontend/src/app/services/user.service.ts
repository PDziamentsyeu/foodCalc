import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
import {User} from "../models/user";
import {Profile} from "../models/profile";
import {Observable} from "rxjs/Observable";

@Injectable()
export class UserService {
    constructor(private http: Http) { }

    getUserFromStorage(){
        return JSON.parse(localStorage.getItem('currentUser'));
    }

    create(user: User) {
        return this.http.post('http://localhost:3000/users', user, this.jwt()).map((response: Response) => {
            localStorage.setItem('currentUser', JSON.stringify(user));
            response.json()
        });
    }

    getFullUserInfo(id) : Observable<Profile>{
        return this.http.get('http://localhost:3000/profiles?user_id=' + id, this.jwt())
            .map((response: Response) => response.json())

    }

    // private helper methods

    private jwt() {
        // create authorization header with jwt token
        let currentUser = JSON.parse(localStorage.getItem('currentUser'));
        if (currentUser && currentUser.token) {
            let headers = new Headers({ 'Authorization': 'Bearer ' + currentUser.token });
            return new RequestOptions({ headers: headers });
        }
    }
}
