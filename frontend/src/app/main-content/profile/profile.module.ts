import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ProfileRoutingModule} from './profile-routing.module';
import {ProfileComponent} from './profile.component';
import {MyprofileComponent} from './myprofile/myprofile.component';
import {MymenusComponent} from './mymenus/mymenus.component';
import {MycalendarComponent} from './mycalendar/mycalendar.component';
import {ReactiveFormsModule} from '@angular/forms';
import { RegistrationComponent } from './registration/registration.component';
import { InterestsComponent } from './interests/interests.component';


@NgModule({
    imports: [
        CommonModule,
        ProfileRoutingModule,
        ReactiveFormsModule
    ],
    declarations: [
        ProfileComponent,
        MyprofileComponent,
        MymenusComponent,
        MycalendarComponent,
        RegistrationComponent,
        InterestsComponent
    ]
})
export class ProfileModule {}
