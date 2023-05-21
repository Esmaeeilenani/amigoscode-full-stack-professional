import {Component, EventEmitter, Output} from '@angular/core';
import {SelectItem} from "primeng/api";
import {CustomerRegistration} from "../../models/customer";
import { FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-manage-customer',
  templateUrl: './manage-customer.component.html',
  styleUrls: ['./manage-customer.component.scss']
})
export class ManageCustomerComponent {

  gender: SelectItem[] = [
    {label: 'MALE ', value: 'MALE'},
    {label: 'FEMALE ', value: 'FEMALE'}
  ]


  @Output()
  submitCustomer: EventEmitter<CustomerRegistration> = new EventEmitter<CustomerRegistration>();
  newCustomer: CustomerRegistration;

  form: FormGroup = new FormGroup({
    name: new FormControl('', [Validators.required, Validators.pattern(/^[A-Za-z\s]*$/)]),
    email: new FormControl('', [Validators.required, Validators.email]),
    age: new FormControl('', [Validators.required, Validators.pattern("^[+]?([.]\\d+|\\d+(\\d+)?)$")]),
    gender: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required, Validators.minLength(8)]),

  })

  selectedGender: string;

  constructor() {


  }

  getControl(name: string) {
    return this.form.controls[name];
  }

  showControlError(name: string) {
    const control = this.form.controls[name];
    return control.invalid && control.dirty
  }

  submitUser() {
    let isValid = true;


    Object.keys(this.form.controls).forEach(key => {
      if (this.form.controls[key].invalid) {
        this.form.controls[key].markAsDirty();
        this.form.controls[key].markAsTouched();

        isValid = false;
      }
    });

    if (!isValid) {
      return;
    }

    this.newCustomer = {
      name: this.getControl("name").value,
      email: this.getControl("email").value,
      age: this.getControl("age").value,
      gender: this.getControl("gender").value,
      password: this.getControl("password").value,

    }
    this.submitCustomer.emit(this.newCustomer);
    this.form.reset();

  }


}
