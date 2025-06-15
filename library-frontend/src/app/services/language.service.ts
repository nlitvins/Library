import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {TranslateService} from '@ngx-translate/core';

export type Language = 'en' | 'lv';

@Injectable({
  providedIn: 'root'
})
export class LanguageService {
  private currentLang = new BehaviorSubject<Language>('en');
  currentLang$ = this.currentLang.asObservable();

  constructor(private translate: TranslateService) {
    // Load saved language preference from localStorage
    const savedLang = localStorage.getItem('language') as Language;
    if (savedLang) {
      this.setLanguage(savedLang);
    } else {
      // Set default language
      this.setLanguage('en');
    }
  }

  setLanguage(lang: Language) {
    this.currentLang.next(lang);
    localStorage.setItem('language', lang);
    document.documentElement.lang = lang;
    this.translate.use(lang);
  }

  getCurrentLanguage(): Language {
    return this.currentLang.value;
  }
}
