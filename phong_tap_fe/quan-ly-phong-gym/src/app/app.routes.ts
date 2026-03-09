import { Component } from '@angular/core';
import { Routes } from '@angular/router';
import { WelcomeComponent } from './pages/manager/welcome/welcome.component';
import { AppComponent } from './app.component';
import { AdminMenuComponent } from './pages/manager/adminMenu/adminMenu.component';
import { LoginComponent } from './pages/login/login.component';
import { HomeComponent } from './pages/home/home.component';
import { ForumUserComponent } from './pages/forumUser/forumUser.component';
import { MenuComponent } from './pages/menu/menu.component';
import { CartComponent } from './pages/cart/cart.component';
import { PersonalPageComponent } from './pages/personalPage/personalPage.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'home', component: HomeComponent },
  { path: 'manager', component: AdminMenuComponent },
  { path: 'login', component: LoginComponent },
  { path: 'forum', component: ForumUserComponent },
  { path: 'menu', component: MenuComponent },
  { path: 'cart', component: CartComponent },
  { path: 'personalPage', component: PersonalPageComponent },
  // { path: '', pathMatch: 'full', redirectTo: '/welcome' },
  // { path: 'welcome', loadChildren: () => import('./pages/manager/welcome/welcome.routes').then(m => m.WELCOME_ROUTES) }
];
