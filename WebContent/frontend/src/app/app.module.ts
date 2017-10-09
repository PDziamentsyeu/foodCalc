import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import {NavigationComponent} from "./navigation/navigation.component";
import {MainContentComponent} from "./main-content/main-content.component";
import {HomepageComponent} from "./main-content/homepage/homepage.component";
import {CalendarComponent} from "./main-content/calendar/calendar.component";
import {MenuListComponent} from "./main-content/menu-list/menu-list.component";
import {DishesListComponent} from "./main-content/dishes-list/dishes-list.component";

const routes: Routes = [
    // basic routes
    { path: '', redirectTo: 'home', pathMatch: 'full' },
    { path: 'home', component: MainContentComponent },
    { path: 'menus', component: MenuListComponent },
    { path: 'dishes', component:  DishesListComponent },
    { path: 'calendar', component:  CalendarComponent  },

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
      MenuListComponent,
      DishesListComponent,
      CalendarComponent
  ],
  imports: [
      BrowserModule,
      RouterModule.forRoot(routes),
  ],
  providers: [],
  bootstrap: [AppComponent]
})


export class AppModule { }
