import { Component, OnInit } from '@angular/core';
import { FormModule } from '../../../common/module/forms.module';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-introduction',
  standalone: true,
  imports: [FormModule, CommonModule, RouterModule],
  templateUrl: './introduction.component.html',
  styleUrls: ['./introduction.component.css'],
})
export class IntroductionComponent implements OnInit {
  constructor() {}

  ngOnInit() {}
}
