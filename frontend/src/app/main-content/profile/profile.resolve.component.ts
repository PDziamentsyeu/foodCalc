import { Injectable } from '@angular/core';
import {
    Router, Resolve,
    ActivatedRouteSnapshot, RouterStateSnapshot
} from '@angular/router';
import {UserService} from '../../services/user.service';
import {Account} from '../../models/account';
import {Observable} from 'rxjs/Observable';
import {Profile} from '../../models/profile';

@Injectable()
export class ProfileResolveComponent implements Resolve<Profile> {
    loading = true;

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Profile>  {
        return this.userService.getFullUserInfo().map(
            profile => {
                console.log(profile);
                return profile;
            },
            error => {
                this.loading = false;
            });
    }

    constructor(private userService: UserService, private router: Router) { }

}
