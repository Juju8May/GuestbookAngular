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

    title = 'Notiz:';
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
        this.service.addCommentToEntry(this.entryId, this.newComment);
        this.navigateToOverview();
    }
    getCommentsByEntryId(entryId: number): EntryCommentEntity[] {
        const entry = this.service.findEntryById(entryId);
        return entry ? entry.comments : [];
    }

}