import { DatePipe } from '@angular/common';
import { Component, inject } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';

@Component({
  selector: 'home',
  standalone: true,
  templateUrl: './home.html',
  styleUrls: ['./home.css'],
})

export class Home {
  title = 'Willkommen!';
  private router = inject(Router);
  navigateToOverview(): void {
    this.router.navigate(['/overview']);
  }
}
