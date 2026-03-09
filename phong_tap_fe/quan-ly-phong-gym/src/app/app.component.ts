import { Component, OnInit } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import { FooterComponent } from './pages/footer/footer.component';
import { HeaderComponent } from './pages/header/header.component';
import { NgIf } from '@angular/common';
import { AuthService } from '../common/shared/service/application/authService';

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet,
    NzIconModule,
    NzLayoutModule,
    NzMenuModule,
    FooterComponent,
    HeaderComponent,
    NgIf,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {
  title = 'Quản lý phòng Gym';
  public isCollapsed = false;
  public isAdmin: boolean = false;

  constructor(private authService: AuthService) {}

  ngOnInit() {
    // Subscribe vào user hiện tại
    this.authService.currentUserSubject$.subscribe((user) => {
      if (user) {
        // role = 1 hoặc 2 => admin
        this.isAdmin = user.role === 1 || user.role === 2;
      } else {
        this.isAdmin = false;
      }
    });
  }
}
