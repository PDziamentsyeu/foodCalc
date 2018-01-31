import {Component, OnInit} from '@angular/core';
import {UserService} from '../services/user.service';
import {Account} from '../models/account';
import {AuthenticationService} from '../services/authentication.service';

@Component({
    selector: 'app-navigation',
    templateUrl: './navigation.component.html'
})
export class NavigationComponent implements OnInit {
    user: Account;

    constructor(private userService: UserService,
                private authService: AuthenticationService) {
    }

    ngOnInit() {
    }

    authorizedUser() {
        this.user = this.userService.getUserFromStorage();
        return this.user;
    }

    logout() {
        this.authService.logout();
    }

}
