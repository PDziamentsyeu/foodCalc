import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {Profile} from '../../models/profile';
import {ActivatedRoute} from '@angular/router';
import {DatePipe} from '@angular/common';

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html'
})
export class ProfileComponent implements OnInit {
    profile: Profile;
    profileForm: FormGroup;


    constructor(private route: ActivatedRoute, private datePipe: DatePipe) {
    }

    ngOnInit() {
        this.route.data
            .subscribe((data: { profile: Profile }) => {
                this.profile = data.profile[0];
            });
        console.log(this.profile);
        this.createForm();
    }

    getKoef() {
        return (this.profileForm.get('weidth').value / Math.pow(this.profileForm.get('height').value / 100, 2)).toFixed(2);
    }

    save() {
        console.log(this.profileForm);
    }

    private createForm() {
        this.profileForm = new FormGroup({
            birthday: new FormControl(this.datePipe.transform(this.profile.birthday, 'yyyy-MM-dd')),
            height: new FormControl(this.profile.height),
            weidth: new FormControl(this.profile.weidth),
            country: new FormControl(this.profile.country),
            city: new FormControl(this.profile.city),
            about: new FormControl(this.profile.about),
            preferences: new FormControl(this.profile.preferences),
        });

    }

}
