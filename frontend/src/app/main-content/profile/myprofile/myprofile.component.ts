import { Component, OnInit } from '@angular/core';
import {Profile} from '../../../models/profile';
import {FormControl, FormGroup} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {DatePipe} from '@angular/common';
import {UserService} from '../../../services/user.service';

@Component({
  selector: 'app-myprofile',
  templateUrl: './myprofile.component.html',
  styleUrls: ['./myprofile.component.css']
})
export class MyprofileComponent implements OnInit {
    profile: Profile;
    profileForm: FormGroup;


    constructor(private route: ActivatedRoute, private datePipe: DatePipe, private userService: UserService) {
    }

    ngOnInit() {
        this.route.data
            .subscribe((data: { profile: Profile }) => {
                this.profile = data.profile;
            });
        this.createForm();
    }

    getKoef() {
        const weight = this.profileForm.get('weight').value;
        const height = this.profileForm.get('height').value;
        return (weight && height) ? (weight / Math.pow(height / 100, 2)).toFixed(2) : 0;
    }

    save() {
        console.log(this.profileForm);
        this.profile.firstName = this.profileForm.get('firstName').value;
        this.profile.lastName = this.profileForm.get('lastName').value;
        this.profile.birthday = this.profileForm.get('birthday').value;
        this.profile.height = this.profileForm.get('height').value;
        this.profile.weight = this.profileForm.get('weight').value;
        this.profile.country = this.profileForm.get('country').value;
        this.profile.city = this.profileForm.get('city').value;
        this.profile.about = this.profileForm.get('about').value;
        this.profile.preferences = this.profileForm.get('preferences').value;
        console.log(this.profile);
        this.userService.saveFullUserInfo(this.profile);

    }

    private createForm() {
        this.profileForm = new FormGroup({
            firstName: new FormControl(this.profile.firstName),
            lastName: new FormControl(this.profile.lastName),
            birthday: new FormControl(this.datePipe.transform(this.profile.birthday, 'yyyy-MM-dd')),
            height: new FormControl(this.profile.height),
            weight: new FormControl(this.profile.weight),
            country: new FormControl(this.profile.country),
            city: new FormControl(this.profile.city),
            about: new FormControl(this.profile.about),
            preferences: new FormControl(this.profile.preferences),
        });

    }
}
