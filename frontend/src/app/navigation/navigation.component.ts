import { Component, OnInit } from '@angular/core';
import {UserService} from "../services/user.service";
import {User} from "../models/user";
import {AuthenticationService} from "../services/authentication.service";

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html'
})
export class NavigationComponent implements OnInit {
  user: User;

  constructor(
      private userService: UserService,
      private authService: AuthenticationService
  ) { }

  ngOnInit() {
  }

  authorizedUser(){
    this.user = this.userService.getUserFromStorage();
    console.log(this.user);
    return this.user;
  }

  logout(){
      this.authService.logout();
  }

}
