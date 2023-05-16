import {Component, OnInit} from '@angular/core';
import {AuthRequest} from "../../models/auth-request";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthenticationService} from "../../services/authentication/authentication.service";
import {HttpErrorResponse} from "@angular/common/http";
import {Message} from "primeng/api";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginRequest: AuthRequest;

  authForm: FormGroup;

  errorMsg: Message[] = [];
  hasError = false;

  constructor(private authService: AuthenticationService, private router: Router) {
  }

  ngOnInit(): void {
    this.createForm();

  }


  private createForm() {
    this.authForm = new FormGroup({
      username: new FormControl("", [Validators.required, Validators.email]),
      password: new FormControl("", Validators.required)
    });
  }

  submit() {

    this.loginRequest = {
      username: this.authForm.get("username")?.value,
      password: this.authForm.get("password")?.value
    }
    this.authService
      .authenticate(this.loginRequest)
      .subscribe({
        next: (res) => {

          this.authService.saveToken(res.jwt);

          this.router.navigate(["/customers"]);
        },
        error: (err: HttpErrorResponse) => {
          if (err.error.statusCode == 403) {
            this.errorMsg = [{
              severity: "error",
              detail: "username or password is wrong"

            }];
            this.hasError = true;
          }
        },

      });
  }

}
