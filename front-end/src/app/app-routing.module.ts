import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CustomerComponent } from './components/customer/customer.component';
import {LoginComponent} from "./components/login/login.component";
import {PrivateGuard} from "./services/guard/private-guard.service";
import {PublicGuard} from "./services/guard/public.guard";

const routes: Routes = [
  {
    path:"", pathMatch:"full", redirectTo:"/login"
  },
  {
    path:"customers", component:CustomerComponent ,canActivate:[PrivateGuard]
  },
  {
    path:"login", component:LoginComponent , canActivate: [PublicGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
