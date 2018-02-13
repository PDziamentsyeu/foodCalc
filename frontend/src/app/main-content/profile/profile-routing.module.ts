import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ProfileComponent} from './profile.component';
import {MycalendarComponent} from './mycalendar/mycalendar.component';
import {MymenusComponent} from './mymenus/mymenus.component';
import {MyprofileComponent} from './myprofile/myprofile.component';
import {ProfileResolveComponent} from "./profile.resolve.component";
import {RegistrationComponent} from "./registration/registration.component";
import {InterestsComponent} from "./interests/interests.component";


const profileRoutes: Routes = [
    {
        path: '',
        component: ProfileComponent,
        children: [
            {
                path: '',
                children: [
                    { path: '', pathMatch: 'full', redirectTo: 'myprofile' },
                    { path: 'registration', component: RegistrationComponent },
                    { path: 'interests', component: InterestsComponent },
                    { path: 'mycalendar', component: MycalendarComponent },
                    { path: 'mymenus', component: MymenusComponent },
                    { path: 'myprofile', component: MyprofileComponent , resolve: {profile: ProfileResolveComponent}}
                ]
            }
        ]
    }
];

@NgModule({
    imports: [
        RouterModule.forChild(profileRoutes)
    ],
    exports: [
        RouterModule
    ]
})
export class ProfileRoutingModule {}
