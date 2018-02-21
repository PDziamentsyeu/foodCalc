import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {} from '@angular/forms';
import {UserService} from '../../services/user.service';
import {Account} from '../../models/account';

@Component({
    selector: 'app-sign-up',
    templateUrl: './sign-up.component.html'
})
export class SignUpComponent implements OnInit {

    model: Account = new Account();
    loading = false;
    confirmPassword: string;

    constructor(private router: Router,
                private userService: UserService) {
    }

    ngOnInit(): void {
        this.confirmPassword = '';
    }

    register() {
        this.loading = true;
        this.userService.create(this.model)
            .subscribe(
                data => {
                    // set success message and pass true paramater to persist the message after redirecting to the login page
                    this.router.navigate(['/login']);
                },
                error => {
                    this.loading = false;
                });
    }

}
