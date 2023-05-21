import {Injectable, Provider} from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor, HTTP_INTERCEPTORS
} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import {AuthenticationService} from "../authentication/authentication.service";
import {Router} from "@angular/router";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService:AuthenticationService, private router: Router) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if (!this.authService.isLoggedIn()) {
      return next.handle(request);
    }


    const jwt = this.authService.getToken();
    if (!jwt) {
      return next.handle(request);
    }


    request = request.clone({
      setHeaders: {
        Authorization: `Bearer ${jwt}`
      },
    });

    return next.handle(request).pipe(
      catchError((err) => {
        if (err.status === 403) {
          this.authService.logout();
          this.router.navigate(['/login']).then();
        }

        return throwError(() => err);
      })
    );


  }
}

export const AuthInterceptorProvider: Provider = {
  provide: HTTP_INTERCEPTORS,
  useClass: AuthInterceptor,
  multi: true,
};

