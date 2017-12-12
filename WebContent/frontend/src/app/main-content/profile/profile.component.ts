import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {Profile} from "../../models/profile";
import {ActivatedRoute, ActivatedRouteSnapshot} from "@angular/router";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html'
})
export class ProfileComponent implements OnInit {
  profile: Profile;
  profileForm: FormGroup;


  constructor(private route: ActivatedRoute){
  }

  ngOnInit() {
      this.route.data
          .subscribe((data: { profile: Profile }) => {
              this.profile = data.profile[0];
          });
      console.log(this.profile);
      this.createForm();
  }

  private createForm() {
      this.profileForm = new FormGroup({
          birthday : new FormControl(this.profile.birthday),
          height: new FormControl(this.profile.heigth),
          weidth: new FormControl(this.profile.weidth),
          country: new FormControl(this.profile.country),
          city: new FormControl(this.profile.city),
          about: new FormControl(this.profile.about),
          preferences: new FormControl(this.profile.preferences),
      });

  }

  getKoef(){
    return 20.12;
  }

}
