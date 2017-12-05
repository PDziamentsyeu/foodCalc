import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import {NavigationComponent} from "./navigation/navigation.component";
import {MainContentComponent} from "./main-content/main-content.component";
import {HomepageComponent} from "./main-content/homepage/homepage.component";
import {InterestingComponent} from "./main-content/interesting/interesting.component";
import {CalculatorComponent} from "./main-content/calculator/calculator.component";
import {DishesListComponent} from "./main-content/dishes-list/dishes-list.component";
import {AboutUsComponent} from "./main-content/about-us/about-us.component";
import {HandbookComponent} from "./main-content/handbook/handbook.component";
import {LoginComponent} from "./main-content/login/login.component";
import {SignUpComponent} from "./main-content/sign-up/sign-up.component";
import {UserService} from "./services/user.service";
import {AuthenticationService} from "./services/authentication.service";
import { EqualDirective } from './directives/equal.directive';

const routes: Routes = [
    // basic routes
    { path: '', redirectTo: 'home', pathMatch: 'full' },
    { path: 'home', component: MainContentComponent },
    { path: 'calculator', component: CalculatorComponent },
    { path: 'dishes', component:  DishesListComponent },
    { path: 'interesting', component:  InterestingComponent  },
    { path: 'handbook', component:  HandbookComponent  },
    { path: 'about-us', component:  AboutUsComponent  },
    { path: 'sign-up', component:  SignUpComponent  },
    { path: 'login', component:  LoginComponent  },
    // authentication demo
    // { path: 'login', component: LoginComponent },
    // {
    //     path: 'protected',
    //     component: ProtectedComponent,
    //     canActivate: [ LoggedInGuard ]
    // }

];

@NgModule({
  declarations: [
      AppComponent,
      NavigationComponent,
      MainContentComponent,
      HomepageComponent,
      CalculatorComponent,
      DishesListComponent,
      InterestingComponent,
      HandbookComponent,
      AboutUsComponent,
      SignUpComponent,
      LoginComponent,
      EqualDirective
  ],
  imports: [
      BrowserModule,
      FormsModule,
      HttpModule,
      RouterModule.forRoot(routes),
  ],
  providers: [
      UserService,
      AuthenticationService
  ],
  bootstrap: [AppComponent]
})


export class AppModule { }
