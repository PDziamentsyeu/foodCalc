import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MainContentComponent } from './main-content.component';
import {HomepageComponent} from "./homepage/homepage.component";
import {CalendarComponent} from "./calendar/calendar.component";
import {DishesListComponent} from "./dishes-list/dishes-list.component";
import {MenuListComponent} from "./menu-list/menu-list.component";

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [MainContentComponent, HomepageComponent, CalendarComponent, MenuListComponent, DishesListComponent]
})
export class MainContentModule { }
