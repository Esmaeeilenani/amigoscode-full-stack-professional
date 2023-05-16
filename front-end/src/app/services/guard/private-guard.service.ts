import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateFn,
  Router,
  RouterStateSnapshot,
  UrlTree
} from '@angular/router';
import { Observable } from 'rxjs';
import {AuthenticationService} from "../authentication/authentication.service";

@Injectable({
  providedIn: 'root'
})
export class PrivateGuard {

  constructor(private authService:AuthenticationService,private router: Router) {
  }
  canActivate:CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot)
    :Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree=>{

    if (this.authService.isLoggedIn()){
      return true;
    }


    return this.router.createUrlTree(['/login']);
  }

}
