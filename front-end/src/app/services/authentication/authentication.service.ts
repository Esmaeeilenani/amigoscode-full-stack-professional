import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AuthRequest} from "../../models/auth-request";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private readonly API_URL = environment.API_URL + "v1/auth";

  private readonly tokenKey = "full-stackJwt";

  constructor(private http: HttpClient) {
  }


  authenticate(authRequest: AuthRequest): Observable<{ jwt: string }> {
    return this.http.post<{ jwt: string }>(this.API_URL + "/login", authRequest);
  }

  saveToken(jwt: string) {
    localStorage.setItem(this.tokenKey, jwt);
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem(this.tokenKey);
  }

  getToken(): string {
    return localStorage.getItem(this.tokenKey) || '';
  }

  logout() {
    localStorage.removeItem(this.tokenKey);
  }

}
