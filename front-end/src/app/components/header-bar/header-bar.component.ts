import {Component} from '@angular/core';
import {MenuItem, PrimeIcons} from "primeng/api";

@Component({
  selector: 'app-header-bar',
  templateUrl: './header-bar.component.html',
  styleUrls: ['./header-bar.component.scss']
})
export class HeaderBarComponent {
  items: Array<MenuItem> = [
    {
      label: "Profile",
      icon: PrimeIcons.USER
    },
    {

      label: "Settings",
      icon: PrimeIcons.COG
    },

    {
      separator: true
    },
    {
      label: "Sign out",
      icon: PrimeIcons.SIGN_OUT
    },

  ];

}
