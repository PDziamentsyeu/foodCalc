import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import 'rxjs/add/operator/map';
import {Constants} from './const';
import {Router} from '@angular/router';


@Injectable()
export class AuthenticationService {

    constructor(private http: Http, private router: Router) { }
    login(email: string, password: string) {
        return this.http.post(Constants.HOME_URL + 'accounts/login', { email: email, password: password })
            .map((response: Response) => {
                // login successful if there's a jwt token in the response
                const token = response.json().token;
                if (token) {
                    // store user details and jwt token in local storage to keep user logged in between page refreshes
                    localStorage.setItem('token', token);
                }
            });
    }

    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem('token');
        this.router.navigate(['login']);
    }

    public isAuthenticated(): boolean {
        const token = localStorage.getItem('token');
        // Check whether the token is expired and return
        // true or false
        return !!token;
    }

}
