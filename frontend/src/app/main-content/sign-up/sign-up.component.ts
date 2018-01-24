import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {  } from '@angular/forms';
import {UserService} from "../../services/user.service";
import {User} from "../../models/user";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html'
})
export class SignUpComponent implements OnInit {

    ngOnInit(): void {
        this.confirmPassword = '';
    }

    model: User = new User();
    loading = false;
    confirmPassword:string;

    constructor(
        private router: Router,
        private userService: UserService) { }

    register() {
        this.loading = true;
        this.userService.create(this.model)
            .subscribe(
                data => {
                    // set success message and pass true paramater to persist the message after redirecting to the login page
                    this.router.navigate(['/profile']);
                },
                error => {
                    this.loading = false;
                });
    }

}
