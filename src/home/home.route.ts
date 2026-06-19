import { Routes } from '@angular/router';
import { Home } from './home';    
import { Overview } from '../overview/overview';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full',
    },
    {
        path: 'overview',
        title: 'Übersicht',
        component: Overview,
    },
];
