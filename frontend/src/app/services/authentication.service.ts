import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import 'rxjs/add/operator/map'

@Injectable()
export class AuthenticationService {

    constructor(private http: Http) { }
    login(username: string, password: string) {
        return this.http.get('http://localhost:3000/users', {params:({ username: username, password: password })})
            .map((response: Response) => {
                // login successful if there's a jwt token in the response
                let user = response.json()[0];
                if (user) {
                    // store user details and jwt token in local storage to keep user logged in between page refreshes
                    localStorage.setItem('currentUser', JSON.stringify(user));
                }

                return user;
            });
    }

    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem('currentUser');
    }

}
