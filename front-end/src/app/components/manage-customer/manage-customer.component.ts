import {Component} from '@angular/core';
import {SelectItem} from "primeng/api";

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
  selectedGender: string;

  constructor() {


  }

}
