import { Component } from '@angular/core';
import { FormsModule, NgModel } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Entry } from '../../entity/entry';
import { EntryService } from '../../service/entry-service';
import { EntryCommentEntity } from '../../entity/entry-comment-entity';
import { User } from '../../entity/user';
import { DatePipe } from '@angular/common';


@Component({
    selector: 'comment',
    standalone: true,
    imports: [DatePipe, FormsModule],
    templateUrl: './entry-comment.html',
    styleUrls: ['./entry-comment.css'],
})
export class EntryComment {

    title = 'Kommentar hinzufügen';
    entryId: number;
    entry: Entry | undefined;
    
    newComment: EntryCommentEntity = {
        id: 0,
        note: '',
        user: {
            id: 0,
            firstname: '',
            lastname: '',
            email: '',
        },
        time: new Date(),
    };

    constructor(
        public router: Router,
        private route: ActivatedRoute,
        private service: EntryService,
    ) {
        this.entryId = Number(this.route.snapshot.paramMap.get('id'));
        this.entry = this.service.findEntryById(this.entryId);
    }

    navigateToOverview(): void {
        this.router.navigate(['/overview']);
    }

    navigateToEdit(): void {
        this.router.navigate(['/edit', this.entryId]);
    }

    addComment(): void {
        if (this.entry && this.newComment.note.trim() && this.newComment.user.firstname && this.newComment.user.lastname && this.newComment.user.email) {
            this.newComment.time = new Date();
            this.service.addCommentToEntry(this.entry.id, this.newComment);
            
            // Formular zurücksetzen
            this.newComment = {
                id: 0,
                note: '',
                user: {
                    id: 0,
                    firstname: '',
                    lastname: '',
                    email: '',
                },
                time: new Date(),
            };
            
            this.navigateToOverview();
        }
    }
    getCommentsByEntryId(entryId: number): EntryCommentEntity[] {
        const entry = this.service.findEntryById(entryId);
        return entry ? entry.comments : [];
    }

}