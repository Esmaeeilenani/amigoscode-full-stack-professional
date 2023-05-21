import { Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {Customer, CustomerRegistration} from "../../models/customer";
import {CustomerService} from "../../services/customer/customer.service";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.scss'],
  providers:[MessageService]
})
export class CustomerComponent implements OnInit {
  displaySideBar: boolean = false;

  customers$: Observable<Customer[]>;
  customers: Customer[];

  constructor(private customerService: CustomerService, private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.fetchAllCustomers();
    // this.customers$ = this.customerService.getAllCustomers();
  }

  saveCustomer(customer: CustomerRegistration) {
    this.customerService
      .saveCustomer(customer)
      .subscribe({
        next: () => this.fetchAllCustomers(),
        error: (err) => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: err.error.message });
        }
      });

  }


  private fetchAllCustomers() {
    this.customerService.getAllCustomers()
      .subscribe({
        next: res => {
          this.customers = res;
        }
      })
  }


}
