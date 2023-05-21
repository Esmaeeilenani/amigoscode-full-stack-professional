import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {ChipsModule} from "primeng/chips";
import { CustomerComponent } from './components/customer/customer.component';
import { MenuBarComponent } from './components/menu-bar/menu-bar.component';
import {AvatarModule} from "primeng/avatar";
import { MenuItemComponent } from './components/menu-item/menu-item.component';
import { HeaderBarComponent } from './components/header-bar/header-bar.component';
import {ButtonModule} from "primeng/button";
import {RippleModule} from "primeng/ripple";
import {MenuModule} from "primeng/menu";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {SidebarModule} from "primeng/sidebar";
import { ManageCustomerComponent } from './components/manage-customer/manage-customer.component';
import {DropdownModule} from "primeng/dropdown";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { LoginComponent } from './components/login/login.component';
import {HttpClientModule} from "@angular/common/http";
import {MessageModule} from "primeng/message";
import { MessagesModule } from 'primeng/messages';
import {AuthInterceptorProvider} from "./services/interceptor/auth.interceptor";
import { CustomerCardComponent } from './components/customer-card/customer-card.component';
import {CardModule} from "primeng/card";
import {BadgeModule} from "primeng/badge";
import {ToastModule} from "primeng/toast";


@NgModule({
  declarations: [
    AppComponent,
    CustomerComponent,
    MenuBarComponent,
    MenuItemComponent,
    HeaderBarComponent,
    ManageCustomerComponent,
    LoginComponent,
    CustomerCardComponent
  ],
  imports: [
    BrowserModule,
    //required for prime NG to work with animation
    BrowserAnimationsModule,
    AppRoutingModule,
    ChipsModule,
    AvatarModule,
    ButtonModule,
    RippleModule,
    MenuModule,
    SidebarModule,
    DropdownModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MessageModule,
    MessagesModule,
    CardModule,
    BadgeModule,
    ToastModule
  ],
  providers: [AuthInterceptorProvider],
  bootstrap: [AppComponent]
})
export class AppModule { }
