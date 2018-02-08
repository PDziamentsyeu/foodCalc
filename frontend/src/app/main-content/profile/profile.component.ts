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
        const weight = this.profileForm.get('weight').value;
        const height = this.profileForm.get('height').value;
        return (weight && height) ? (weight / Math.pow(height / 100, 2)).toFixed(2) : 0;
    }

    save() {
        console.log(this.profileForm);
    }

    private createForm() {
        this.profileForm = new FormGroup({
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
