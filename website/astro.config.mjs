import { defineConfig } from 'astro/config';
import starlight from '@astrojs/starlight';

export default defineConfig({
    site: 'https://aldefy.github.io',
    base: '/compose-contextual-appbar',
    integrations: [
        starlight({
            title: 'Compose Contextual AppBar',
            description: 'Animated contextual top app bar for Compose Multiplatform',
            social: {
                github: 'https://github.com/aldefy/compose-contextual-appbar',
            },
            customCss: ['./src/styles/custom.css'],
            sidebar: [
                {
                    label: 'Getting Started',
                    items: [
                        { label: 'Installation', link: '/getting-started/installation/' },
                        { label: 'Quick Start', link: '/getting-started/quick-start/' },
                    ],
                },
                {
                    label: 'Guide',
                    items: [
                        { label: 'ContextualTopAppBar', link: '/guide/contextual-top-app-bar/' },
                        { label: 'MaterialContextualTopAppBar', link: '/guide/material-contextual-top-app-bar/' },
                        { label: 'Customization', link: '/guide/customization/' },
                    ],
                },
                {
                    label: 'API Reference',
                    items: [
                        { label: 'ContextualTopAppBar', link: '/api/contextual-top-app-bar/' },
                        { label: 'MaterialContextualTopAppBar', link: '/api/material-contextual-top-app-bar/' },
                        { label: 'Defaults & Colors', link: '/api/contextual-top-app-bar-defaults/' },
                    ],
                },
            ],
        }),
    ],
});
