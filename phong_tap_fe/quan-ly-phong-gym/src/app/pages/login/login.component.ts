import { Component, HostListener, OnInit } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule, NgIf } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { LoginUserComponent } from "./loginUser/loginUser.component";
import { RegisterComponent } from "./register/register.component";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    NgIf,
    CommonModule,
    HttpClientModule,
    LoginUserComponent,
    RegisterComponent
],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  public activeTab: 'login' | 'register' = 'login';
  public isScrolled = false;

  constructor(private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.queryParams.subscribe((params) => {
      if (params['tab'] === 'login') {
        this.activeTab = 'login';
      }
    });
  }

  @HostListener('window:scroll', [])
  onWindowScroll() {
    this.isScrolled = window.pageYOffset > 50;
  }

  setActiveTab(tab: 'login' | 'register') {
    this.activeTab = tab;
  }
}
