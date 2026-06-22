import { Routes } from '@angular/router';
import { Edit } from './app/entry/edit/edit';
import { Home } from './home/home';
import { Overview } from './overview/overview';
import { CommentANote } from './app/entry/comment/entry-comment';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full',
    },
    {
        path: 'home',
        title: 'Startseite',
        component: Home,
    },
    {
        path: 'overview',
        title: 'Übersicht',
        component: Overview,
    },
    {
        path: 'edit/:id',
        title: 'Eintrag bearbeiten',
        component: Edit,
    },
    {
        path: 'comment/:id',
        title: 'Kommentar hinzufügen',
        component: EntryComment,
    }
];
