import {Component, Input, OnInit} from '@angular/core';
import {Customer} from "../../models/customer";

@Component({
  selector: 'app-customer-card',
  templateUrl: './customer-card.component.html',
  styleUrls: ['./customer-card.component.scss']
})
export class CustomerCardComponent implements OnInit {

  @Input()
  customer: Customer;

  @Input()
  cusIndex: number;

  randomImageNum: number

  ngOnInit(): void {
    this.randomImageNum = Math.floor(Math.random() * 50) + 1;
  }

  getCustomerImage(gender: string | undefined): string {


    return `https://randomuser.me/api/portraits/${gender == "MALE" ? "men" : "women"}/${this.randomImageNum}.jpg`;
  }


}
