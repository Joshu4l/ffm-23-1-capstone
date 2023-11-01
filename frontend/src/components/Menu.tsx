import "./Menu.css";

interface MenuProps {
    isOpen: boolean;
    closeMenu: () => void;
}

export default function Menu({ isOpen, closeMenu }: MenuProps) {

    return (
        <div className={ `menu ${isOpen ? "open" : ""}` }>

            <ul>
                <li><strong>Option 1</strong></li>
                <li><strong>Option 2</strong></li>
                <li><strong>Option 3</strong></li>
            </ul>

            <button id="collapse-menu-button"
                onClick={closeMenu}
            >

                &#xd7;
            </button>

        </div>
    );

}