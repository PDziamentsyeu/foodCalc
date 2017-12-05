import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
import {User} from "../models/user";

@Injectable()
export class UserService {
    constructor(private http: Http) { }

    getUserFromStorage(){
        return JSON.parse(localStorage.getItem('currentUser'));
    }

    create(user: User) {
        return this.http.post('http://localhost:3000/users', user, this.jwt()).map((response: Response) => response.json());
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
