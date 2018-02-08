import {Component, OnInit} from '@angular/core';
import {UserService} from '../services/user.service';
import {Account} from '../models/account';
import {AuthenticationService} from '../services/authentication.service';

@Component({
    selector: 'app-navigation',
    templateUrl: './navigation.component.html'
})
export class NavigationComponent implements OnInit {

    constructor(private userService: UserService,
                private authService: AuthenticationService) {
    }

    ngOnInit() {
    }

    authorizedUser() {
        const token = this.userService.getUserFromStorage();
        return token;
    }

    logout() {
        this.authService.logout();
    }

}
