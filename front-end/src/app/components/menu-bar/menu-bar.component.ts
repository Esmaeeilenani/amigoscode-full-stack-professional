import { Component } from '@angular/core';
import {MenuItem, PrimeIcons} from "primeng/api";

@Component({
  selector: 'app-menu-bar',
  templateUrl: './menu-bar.component.html',
  styleUrls: ['./menu-bar.component.scss']
})
export class MenuBarComponent {

  menu: Array<MenuItem> = [{
    label: 'Home',
    icon: PrimeIcons.HOME,
  },
    {
      label: 'Customers',
      icon: PrimeIcons.USERS
    },
    {
      label: 'Settings',
      icon: PrimeIcons.COG
    }
  ];

}
