package com.example.autouroute.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

enum class AppLanguage {
    ARABIC, FRENCH
}

object Strings {
    private var currentLanguageState by mutableStateOf(AppLanguage.FRENCH)

    fun setLanguage(language: AppLanguage) {
        currentLanguageState = language
    }

    fun getLanguage() = currentLanguageState

    // Generic helper to pick the right string
    private fun s(ar: String, fr: String): String {
        return if (currentLanguageState == AppLanguage.ARABIC) ar else fr
    }

    val ARABIC_TITLE = "تونس للطرقات السيارة"
    val LATIN_TITLE = "TUNISIE AUTOROUTES"

    val LANGUAGE_TITLE get() = s("اختر اللغة", "Choisir une langue")
    val LANGUAGE_SUBTITLE get() = s("الرجاء اختيار اللغة التي تناسبك", "Veuillez sélectionner la langue qui vous correspond")
    val ARABE = "Arabe"
    val FRANCAIS = "Français"
    val VALIDER get() = s("تأكيد", "Valider")
    val LANGUAGE_NOTE get() = s("ملاحظة: إذا كنت ترغب في تغيير اللغة، يمكنك الوصول إلى واجهة \"الملف الشخصي\"", "NB: Si vous voulez changer la langue, vous pouvez accéder à l'interface \"Profil\"")

    val WELCOME_TITLE get() = s("مرحباً!", "Bienvenue !")
    val WELCOME_DESC get() = s("سنرافقك لاكتشاف تطبيقك بلدية مساكن والاستماع إليك.", "Nous allons vous accompagner pour découvrir votre application Commune de Messadine et être à votre écoute.")
    val DECOUVRIR get() = s("اكتشف", "Découvrir")

    val LOGIN_TITLE get() = s("تسجيل الدخول", "Se connecter")
    val LOGIN_SUBTITLE get() = s("لدي حساب بالفعل", "J'ai déjà un compte")
    val REGISTER_TITLE get() = s("تسجيل", "S'inscrire")
    val REGISTER_SUBTITLE get() = s("ليس لدي حساب", "Je n'ai pas de compte")
    val PRENOM get() = s("الاسم الأول", "Prénom")
    val NOM get() = s("اللقب", "Nom")
    val GENRE get() = s("الجنس", "Genre")
    val AGE get() = s("العمر", "Age")
    val PHONE get() = s("رقم الهاتف", "Numéro de téléphone")

    val AUTH_MODAL_TITLE get() = s("رسالة التوثيق", "Message d'authentification")
    val AUTH_MODAL_MSG get() = s("أدخل رمز التوثيق الذي تلقيته على رقمك", "Faites entrer le code d'authentification que vous avez reçu sur votre numéro")
    val ANNULER get() = s("إلغاء", "Annuler")

    val SERVICES_TITLE get() = s("الخدمات", "Services")
    val SERVICES_SUBTITLE get() = s("نحن هنا في خدمتكم.", "Nous sommes là, à votre service.")
    val ENTRETIEN_ROUTES get() = s("صيانة الطرقات", "Entretient des Routes")
    val PROBLEME_PEAGE get() = s("مشكلة في الاستخلاص", "Probléme de péage")
    val ECLAIRAGE_DEFECTUEUX get() = s("إنارة معطلة", "Eclirage défectueux")
    val PROBLEME_SECURITE get() = s("مشكلة أمنية", "Probléme de sécurité")
    val PROBLEME_SERVICES_USAGERS get() = s("مشكلة في خدمات المستخدمين", "Probléme de services aux usagers")

    val REPORT_INSTRUCTIONS get() = s("يرجى إضافة صورة ثم اختيار اقتراحك", "Veilliez ajouter une photo puis choisir votre propsition")
    val CHOISIR_PHOTO get() = s("اختر صورة", "Choisir une photo")

    val PHOTO_TAKE get() = s("إلتقاط صورة", "Prendre une photo")
    val PHOTO_GALLERY get() = s("استيراد من المعرض", "Importer de la galerie")

    val LOCATION_TITLE get() = s("الموقع", "Localisation")
    val LOCATION_SHARE get() = s("مشاركة موقعك", "Partager votre position")
    val LOCATION_WRITE get() = s("كتابة موقعك", "Ecrire votre position")
    val LOCATION_ACQUIRING get() = s("جارٍ تحديد الموقع...", "Acquisition de la position GPS...")
    val LOCATION_ACQUIRED get() = s("تم تحديد الموقع بنجاح", "Position GPS acquise avec succès")
    val LOCATION_REQUIRED get() = s("يجب تفعيل الموقع لإرسال الشكوى", "La géolocalisation est obligatoire pour envoyer la réclamation")
    val LOCATION_PERMISSION_DENIED get() = s("تم رفض إذن الموقع. يرجى تفعيله من الإعدادات", "Permission de localisation refusée. Veuillez l'activer dans les paramètres")
    val LOCATION_FAILED get() = s("فشل في تحديد الموقع. حاول مرة أخرى", "Échec de localisation. Veuillez réessayer")

    val SUCCESS_TITLE get() = s("عملية ناجحة", "Opération réussite")
    val SUCCESS_SUBTITLE get() = s("أحسنت في هذه المبادرة!", "Bravo pour cette initiative!")
    val SUCCESS_THANK1 get() = s("نشكرك جزيل الشكر على الإبلاغ عن المشكلة في تطبيقنا.", "Nous vous remercions chaleureusement d'avoir signalé le problème dans notre application.")
    val SUCCESS_THANK2 get() = s("تساعدنا مساهمتك على تحسين خدماتنا باستمرار. نحن نأخذ هذا الأمر على محمل الجد ونعمل بنشاط على حل الموقف.", "Votre contribution nous aide à améliorer constamment nos services. Nous prenons cela très au sérieux et nous travaillons activement à résoudre la situation.")

    val OBSERVATIONS_TITLE get() = s("الملاحظات", "Observations")
    val PROFILE_TITLE get() = s("الملف الشخصي", "Profil")
    val PROFILE_PHONE_LABEL get() = s("رقم الهاتف:", "Numéro de télephone :")
    val PROFILE_CHANGE_LANG get() = s("تغيير اللغة:", "Changer la langue :")
    val PROFILE_CONDITIONS get() = s("الشروط والخصوصية", "Conditions et Confidentialités")
    val PROFILE_LOGOUT get() = s("تسجيل الخروج", "De déconnecter")

    val ADMIN_TITLE get() = s("الإدارة", "Administration")
    val ADMIN_ADRESSE get() = s("العنوان:", "Adresse :")
    val ADMIN_EMAIL get() = s("البريد الإلكتروني:", "Email :")
    val ADMIN_PHONE get() = s("رقم الهاتف:", "Numéro de télephone :")
    val ADMIN_FAX get() = s("رقم الفاكس:", "Numéro de Fax :")
    val ADMIN_HORAIRE get() = s("ساعات العمل", "Horaire de travail")
    val ADMIN_LUNDI get() = s("الاثنين - الخميس:", "Lundi - Jeudi :")
    val ADMIN_VENDREDI get() = s("الجمعة:", "Vendredi :")
    val ADMIN_SAMEDI get() = s("السبت - الأحد:", "Samedi-Dimanche :")
}
